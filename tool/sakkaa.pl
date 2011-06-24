#!/usr/bin/perl

# Copyright 2011 (c) Tuomas Starck
# This script translates PGN books (such as the one Gnuchess
# uses) to series of Sakki commands and moves. Useful when
# wanting to create great amounts of input for testing and
# profiling.

use strict;

sub Line($) {
	my @ret;

	foreach (split /\s+/) {
		s/\d+\.//;
		next if (/^1/ or /^0-1/);
		push @ret, $_;
	}

	return @ret;
}

sub Print($$) {
	my ($nro, $rivi) = @_;
	print "new\n";
	foreach (@$rivi) { print "$_\n"; }
}

sub Main($) {
	my ($kirja) = @_;

	my $nro = 0;
	my $tila = 0;
	my $rivi = [];

	open BOOK, $kirja or die $!;

	while (<BOOK>) {
		chomp;

		if ($tila == 0) {
			if (/^\[/ or /^\s*$/) {
				$tila = 0;
			}
			else {
				$nro = $.;
				$tila = 1;
				push @$rivi, Line($_);
			}
		}
		elsif ($tila == 1) {
			if (/^\s*$/) {
				Print($nro, $rivi);
				$rivi = [];
				$tila = 0;
			}
			else {
				push @$rivi, Line($_);
			}
		}
	}

	close BOOK;

	return 0;
}

exit Main(shift);
